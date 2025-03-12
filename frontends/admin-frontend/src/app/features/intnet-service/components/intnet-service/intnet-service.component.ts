import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { PodData, ServiceKubernetesData } from "../../models/IntnetService";
import { IntnetServiceService } from "../../services/intnet-service.service";
import { CommonModule } from "@angular/common";
import { faArrowLeftRotate, faCaretDown, faCaretUp, faSpinner } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";

@Component({
    selector: "app-intnet-service",
    imports: [CommonModule, FontAwesomeModule],
    templateUrl: "./intnet-service.component.html",
    styleUrl: "./intnet-service.component.css",
})
export class IntnetServiceComponent implements OnInit {
    serviceName?: string;
    serviceData?: ServiceKubernetesData;
    isLoading: boolean = false;

    openPodName?: string;

    logs: string[] = [];

    constructor(
        private readonly intnetServiceService: IntnetServiceService,
        private readonly activatedRoute: ActivatedRoute
    ) {}

    ngOnInit(): void {
        this.activatedRoute.paramMap.subscribe((params) => {
            this.serviceName = params.get("serviceName") ?? undefined;
            if (!this.serviceName) return;

            this.loadPods(this.serviceName);
        });
    }

    private loadPods(serviceName: string): void {
        this.isLoading = true;

        this.intnetServiceService.getServiceWithPods(serviceName).subscribe(
            (data) => {
                console.log("Data", data);
                this.serviceData = data;
                this.isLoading = false;
            },
            (error) => {
                console.error("Error fetching service pods: ", error);
                this.isLoading = false;
            }
        );
    }

    openPod(podName: string): void {
        if (this.openPodName == podName) {
            this.openPodName = undefined;
            return;
        }
        this.openPodName = podName;

        if (!this.serviceName) return;

        this.intnetServiceService.streamPodLogs(this.serviceName, this.openPodName, "default").subscribe(
            (logLine) => {
                this.logs.push(logLine);
                console.log("Log line", logLine);
            },
            (error) => {
                console.error("Error streaming logs:", error);
            },
            () => {
                console.log("Log stream completed");
            }
        );
    }

    refresh(): void {
        if (!this.serviceName) return;
        this.loadPods(this.serviceName);
    }

    restart(): void {
        if (!this.serviceName) return;

        this.intnetServiceService.rolloutRestartDeployments([this.serviceName], this.serviceData?.namespace).subscribe(
            (data) => {
                this.loadPods(this.serviceName ?? "");
            }
        );
    }

    faCaretDown = faCaretDown;
    faCaretUp = faCaretUp;
    faArrowLeftRotate = faArrowLeftRotate;
    faSpinner = faSpinner;
}

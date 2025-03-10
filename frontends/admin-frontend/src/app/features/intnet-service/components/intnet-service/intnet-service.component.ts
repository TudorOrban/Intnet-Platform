import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { PodData, ServiceKubernetesData } from "../../models/IntnetService";
import { IntnetServiceService } from "../../services/intnet-service.service";
import { CommonModule } from "@angular/common";
import { faCaretDown, faCaretUp } from "@fortawesome/free-solid-svg-icons";
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

    openPodName?: string;

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

    private loadPods(serviceName: string) {
        this.intnetServiceService.getServiceWithPods(serviceName).subscribe(
            (data) => {
                console.log("Data", data);
                this.serviceData = data;
            },
            (error) => {
                console.error("Error fetching service pods: ", error);
            }
        );
    }

    openPod(podName: string) {
        if (this.openPodName == podName) {
            this.openPodName = undefined;
        } else {
            this.openPodName = podName;
        }
    }

    faCaretDown = faCaretDown;
    faCaretUp = faCaretUp;
}

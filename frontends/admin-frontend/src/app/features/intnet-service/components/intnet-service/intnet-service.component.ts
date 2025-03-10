import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { PodData, ServiceKubernetesData } from "../../models/IntnetService";
import { IntnetServiceService } from "../../services/intnet-service.service";
import { CommonModule } from "@angular/common";

@Component({
    selector: "app-intnet-service",
    imports: [CommonModule],
    templateUrl: "./intnet-service.component.html",
    styleUrl: "./intnet-service.component.css",
})
export class IntnetServiceComponent implements OnInit {
    serviceName?: string;
    serviceData?: ServiceKubernetesData;

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
}

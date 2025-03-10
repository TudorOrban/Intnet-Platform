import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";

@Component({
    selector: "app-intnet-service",
    imports: [],
    templateUrl: "./intnet-service.component.html",
    styleUrl: "./intnet-service.component.css",
})
export class IntnetServiceComponent implements OnInit {
    serviceName?: string;
    constructor(private readonly activatedRoute: ActivatedRoute) {}

    ngOnInit(): void {
        this.activatedRoute.paramMap.subscribe((params) => {
            this.serviceName = params.get("serviceName") ?? undefined;

        });
    }
}

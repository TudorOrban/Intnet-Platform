import { Component } from "@angular/core";
import { sidebarItems } from "../../config/sidebarItems";
import { Router } from "@angular/router";
import { UIItem } from "../../../../shared/types/common";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { faHome } from "@fortawesome/free-solid-svg-icons";
import { CommonModule } from "@angular/common";

@Component({
    selector: "app-sidebar",
    imports: [CommonModule, FontAwesomeModule],
    templateUrl: "./sidebar.component.html",
    styleUrl: "./sidebar.component.css",
})
export class SidebarComponent {
    currentRoute?: string;
    sidebarItems = sidebarItems;


    constructor(
        private readonly router: Router
    ) {}

    navigateTo(item?: UIItem) {
        if (!item) {
            return;
        }

        this.currentRoute = item.value;
        this.router.navigate([item?.link ?? "not-found"]);
    }

    faHome = faHome;
}

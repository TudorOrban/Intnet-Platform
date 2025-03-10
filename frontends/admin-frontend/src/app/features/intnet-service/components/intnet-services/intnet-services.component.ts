import { Component, OnInit } from '@angular/core';
import { IntnetServiceService } from '../../services/intnet-service.service';
import { IntnetService } from '../../models/IntnetService';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faArrowLeftRotate, faSearch } from '@fortawesome/free-solid-svg-icons';
import { UIUtilService } from '../../../../shared/utils/ui-util.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-intnet-services',
  imports: [CommonModule, FontAwesomeModule, FormsModule],
  templateUrl: './intnet-services.component.html',
  styleUrl: './intnet-services.component.css'
})
export class IntnetServicesComponent implements OnInit {
    services?: IntnetService[];
    searchedServices?: IntnetService[];

    searchQuery: string = "";

    constructor(
        private readonly intnetServiceService: IntnetServiceService,
        readonly uiUtilService: UIUtilService
    ) {}
    
    ngOnInit(): void {
        this.intnetServiceService.getServices("default").subscribe(
            (data) => {
                console.log("Data:", data);
                this.services = data;
                this.searchedServices = this.services;
            },
            (error) => {
                console.error("Error fetching services:", error);
            }
        );
    }

    handleSearchQueryChange(): void {
        this.searchedServices = this.services?.filter(service => service.name.includes(this.searchQuery));
    }

    faSearch = faSearch;
    faArrowLeftRotate = faArrowLeftRotate;
}

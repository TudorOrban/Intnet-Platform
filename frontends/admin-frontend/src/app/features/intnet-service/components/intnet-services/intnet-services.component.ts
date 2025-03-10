import { Component, OnInit } from '@angular/core';
import { IntnetServiceService } from '../../services/intnet-service.service';

@Component({
  selector: 'app-intnet-services',
  imports: [],
  templateUrl: './intnet-services.component.html',
  styleUrl: './intnet-services.component.css'
})
export class IntnetServicesComponent implements OnInit {

    constructor(private readonly intnetServiceService: IntnetServiceService) {}
    
    ngOnInit(): void {
        this.intnetServiceService.getServices("default").subscribe(
            (data) => {
                console.log("Data:", data);
            },
            (error) => {
                console.error("Error fetching services:", error);
            }
        );
    }
}

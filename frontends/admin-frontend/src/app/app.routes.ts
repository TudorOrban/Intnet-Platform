import { Routes } from '@angular/router';
import { HomeComponent } from './core/main/components/home/home.component';
import { IntnetServicesComponent } from './features/intnet-service/components/intnet-services/intnet-services.component';
import { IntnetServiceComponent } from './features/intnet-service/components/intnet-service/intnet-service.component';

export const routes: Routes = [
    {
        path: "home",
        component: HomeComponent
    },
    {
        path: "services",
        component: IntnetServicesComponent
    },
    {
        path: "services/:serviceName",
        component: IntnetServiceComponent
    }
];

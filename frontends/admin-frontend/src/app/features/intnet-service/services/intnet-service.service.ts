import { Injectable } from "@angular/core";
import { environment } from "../../../../environments/environment";
import { IntnetService } from "../models/IntnetService";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";

@Injectable({
    providedIn: "root"
})
export class IntnetServiceService {
    private apiUrl = `${environment.apiUrl}/intnet-services`;

    constructor(private readonly http: HttpClient) {}

    getServices(namespace?: string): Observable<IntnetService[]> {
        return this.http.get<IntnetService[]>(this.apiUrl, { params: { namespace: namespace ?? "default" }});
    } 

}
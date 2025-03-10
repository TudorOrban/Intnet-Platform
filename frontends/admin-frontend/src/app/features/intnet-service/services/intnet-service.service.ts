import { Injectable } from "@angular/core";
import { environment } from "../../../../environments/environment";
import { IntnetService, PodData, ServiceKubernetesData } from "../models/IntnetService";
import { Observable } from "rxjs";
import { HttpClient, HttpParams } from "@angular/common/http";

@Injectable({
    providedIn: "root"
})
export class IntnetServiceService {
    private apiUrl = `${environment.apiUrl}/intnet-services`;

    constructor(private readonly http: HttpClient) {}

    getServices(namespace?: string): Observable<IntnetService[]> {
        return this.http.get<IntnetService[]>(this.apiUrl, { params: { namespace: namespace ?? "default" }});
    } 

    getServiceWithPods(serviceName: string, namespace?: string): Observable<ServiceKubernetesData> {
        return this.http.get<ServiceKubernetesData>(`${this.apiUrl}/${serviceName}/pods`, { params: { namespace: namespace ?? "default" }});
    }

    streamPodLogs(serviceName: string, podName: string, namespace?: string, containerName?: string): Observable<string> {
        let params = new HttpParams();
        if (namespace) {
            params = params.set("namespace", namespace);
        }
        if (containerName) {
            params = params.set("containerName", containerName);
        }

        return new Observable<string>(observer => {
            const eventSource = new EventSource(`${this.apiUrl}/${serviceName}/pods/${podName}/logs?${params.toString()}`);

            eventSource.onmessage = (event) => {
                observer.next(event.data);
            }

            eventSource.onerror = (error) => {
                observer.error(error);
            }

            eventSource.onopen = () => {
                console.log("EventSource opened");
            }

            return () => {
                eventSource.close();
                console.log("EventSource closed");
            }
        });
    }

}
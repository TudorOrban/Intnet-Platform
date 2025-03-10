import { Injectable } from "@angular/core";

@Injectable({
    providedIn: "root"
})
export class UIUtilService {

    formatEnum(enumString?: string): string | undefined {
        if (!enumString) return undefined;

        return enumString.charAt(0) + enumString.substring(1).toLowerCase();
    }
}
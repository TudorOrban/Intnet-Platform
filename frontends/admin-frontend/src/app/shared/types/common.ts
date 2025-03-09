import { IconDefinition } from "@fortawesome/free-solid-svg-icons";

export interface UIItem {
    label: string;
    value: string;
    link?: string;
    icon?: IconDefinition;
}
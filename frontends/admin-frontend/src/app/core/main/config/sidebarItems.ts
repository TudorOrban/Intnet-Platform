import { faHome, faTableColumns } from "@fortawesome/free-solid-svg-icons";
import { UIItem } from "../../../shared/types/common";

export const sidebarItems: UIItem[] = [
    {
        label: "Home",
        value: "home",
        link: "home",
        icon: faHome
    },
    {
        label: "Services",
        value: "services",
        link: "services",
        icon: faTableColumns
    }
];
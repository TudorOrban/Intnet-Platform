variable "resource_group_name" {
    type = string
}

variable "location" {
    type = string
}

variable "microservices" {
    type = map(object({
        sku_name          = optional(string, "B_Standard_B2ms")
        storage_mb        = optional(number, 32768)
        version           = optional(string, "14")
        database_name     = optional(string)
        zone              = optional(string, "2")
    }))
    default = {}
}

variable "subnet_prefixes" {
    type = map(list(string))
    description = "Map of subnet prefixes"
}

variable "postgres_server_name" {
    type = string
}

variable "postgres_admin_user" {
    type = string
}

variable "postgres_admin_password" {
    type = string
    sensitive = true
}

variable "postgres_sku_name" {
    type = string
    default = "Standard_B1ms"
}

variable "postgres_storage_mb" {
    type = number
    default = 32768
}

variable "postgres_version" {
    type = string
    default = "14"
}

variable "subnet_id" {
    type = string
    description = "Subnet ID for the PostgreSQL server"
}

variable "allowed_aks_subnet_ids" {
    type = list(string)
    description = "List of AKS subnet IDs allowed to access the PostgreSQL server"
}
variable "resource_group_name" {
    type = string
    description = "The name of the resource group."
}

variable "location" {
    type = string
    description = "The Azure region where resources will be created."
}

variable "eventhub_namespace_name" {
    type = string
    description = "The name of the Event Hubs namespace."
}

variable "eventhub_name" {
    type = string
    description = "The name of the Event Hub."
}

variable "eventhub_sku" {
    type = string
    description = "The SKU of the Event Hubs namespace."
}

variable "eventhub_partition_count" {
    type = number
    description = "The partition count of the event hub"
}

variable "eventhub_message_retention" {
    type = number
    description = "The message retention of the event hub"
}
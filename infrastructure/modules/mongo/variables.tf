variable "resource_group_name" {
    type = string
    description = "The name of the resource group."
}

variable "location" {
    type = string
    description = "The Azure region where resources will be created."
}

variable "mongo_account_name" {
    type = string
    description = "The name of the CosmosDB Mongo account."
}

variable "subnet_id" {
    type = string
    description = "The ID of the subnet for the CosmosDB account."
}

variable "allowed_aks_subnet_ids" {
    type = list(string)
    description = "A list of subnet IDs that are allowed to access the CosmosDB account."
}

variable "mongo_database_names" {
    type = list(string)
    description = "A list of MongoDB database names."
}

variable "mongo_offer_type" {
    type = string
    description = "The offer type for the CosmosDB account."
}

variable "mongo_server_version" {
  type = string
  description = "The MongoDB server version."
}
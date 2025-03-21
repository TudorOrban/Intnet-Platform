terraform {
    required_version = ">= 1.0"
}

terraform {
    required_providers {
        azurerm = {
            source = "hashicorp/azurerm"
            version = "~> 3.0" 
        }
        kubernetes = {
            source = "hashicorp/kubernetes"
            version = "~> 2.0"
        }
        helm = {
            source = "hashicorp/helm"
            version = "~> 2.0"
        }
    }
}

provider "azurerm" {
    features {}
    subscription_id = var.subscription_id
    tenant_id = var.tenant_id
}

provider "kubernetes" {
    host = module.aks.kube_config.host
    client_certificate = base64decode(module.aks.kube_config.client_certificate)
    client_key = base64decode(module.aks.kube_config.client_key)
    cluster_ca_certificate = base64decode(module.aks.kube_config.cluster_ca_certificate)
}

provider "helm" {
    kubernetes {
        host = module.aks.kube_config.host
        client_certificate = base64decode(module.aks.kube_config.client_certificate)
        client_key = base64decode(module.aks.kube_config.client_key)
        cluster_ca_certificate = base64decode(module.aks.kube_config.cluster_ca_certificate)
    }
}
# output "microservice_db_fqdns" {
#     value = module.postgres.microservice_db_fqdns
# }

# output "kube_config" {
#     value = module.aks.kube_config
#     sensitive = true
# }

output "eventhub_connection_string" {
    value = module.kafka.eventhub_namespace_connection_string
    sensitive = true
}

output "eventhub_name" {
    value = module.kafka.eventhub_name
}

output "eventhub_namespace_name" {
    value = module.kafka.eventhub_namespace_name
}

# output "grafana_url" {
#     value = module.monitoring.grafana_url
# }


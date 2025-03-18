# output "microservice_db_fqdns" {
#     value = module.postgres.microservice_db_fqdns
# }

# output "kube_config" {
#     value = module.aks.kube_config
#     sensitive = true
# }

output "grafana_url" {
    value = module.monitoring.grafana_url
}

output "microservice_db_fqdns" {
    value = { for key, value in azurerm_postgresql_flexible_server.microservice_db : key => value.fqdn }
}

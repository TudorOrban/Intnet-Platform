output "mongo_database_urls" {
    value = {
        for db in azurerm_cosmosdb_mongo_database.mongo_db :
        db.name => azurerm_cosmosdb_account.mongo.connection_strings[0]
    }
    sensitive = true
}
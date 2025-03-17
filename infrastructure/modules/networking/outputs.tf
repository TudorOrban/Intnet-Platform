output "subnet_ids" {
    value = {
        for name, subnet in azurerm_subnet.subnet : name => subnet.id
    }
}
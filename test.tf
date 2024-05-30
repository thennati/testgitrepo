resource "kafka_acl" "acl" {
  for_each = {
    for acl_pair in var.kafka_acl : 
    "${acl_pair.acl_principal}_${join("_", acl_pair.resource_name)}_${acl_pair.acl_operation}" => acl_pair
  }

  resource_name       = each.value.resource_name
  resource_type       = each.value.resource_type
  acl_principal       = each.value.acl_principal
  acl_host            = "*"
  acl_operation       = each.value.acl_operation
  acl_permission_type = each.value.acl_permission_type
  resource_pattern_type_filter = each.value.resource_pattern_type_filter
}



resource "kafka_acl" "acl" {
  for_each = {
    for acl_pair in var.kafka_acl : 
    for resource_name in acl_pair.resource_name :
    "${acl_pair.acl_principal}_${resource_name}_${acl_pair.acl_operation}" => {
      resource_name                = resource_name
      resource_type                = acl_pair.resource_type
      acl_principal                = acl_pair.acl_principal
      acl_operation                = acl_pair.acl_operation
      acl_permission_type          = acl_pair.acl_permission_type
      resource_pattern_type_filter = acl_pair.resource_pattern_type_filter
    }
  }

  resource_name                = each.value.resource_name
  resource_type                = each.value.resource_type
  acl_principal                = each.value.acl_principal
  acl_host                     = "*"
  acl_operation                = each.value.acl_operation
  acl_permission_type          = each.value.acl_permission_type
  resource_pattern_type_filter = each.value.resource_pattern_type_filter
}
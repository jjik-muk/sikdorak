data "terraform_remote_state" "vpc" {
  backend = "local"

  config = {
    path = "${path.module}/../vpc/terraform.tfstate"
  }
}

variable "db_username" {
  description = "RDS root user"
  type        = string
  sensitive   = true
}

variable "db_password" {
  description = "RDS root user password"
  type        = string
  sensitive   = true
}

data "terraform_remote_state" "vpc" {
   backend = "local"

    config = {
        path = "${path.module}/../vpc/terraform.tfstate"
    }
}

data "terraform_remote_state" "ecr" {
   backend = "local"

    config = {
        path = "${path.module}/../ecr/terraform.tfstate"
    }
}

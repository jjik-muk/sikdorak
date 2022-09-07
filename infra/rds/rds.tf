# TODO
# [x] subnet group
# [] db instance
# [] parameter group
# [] input : db id/pw  variable
# [] ouput varibale : hostname, port
# [] +a) read replica 
# ref : https://learn.hashicorp.com/tutorials/terraform/aws-rds?in=terraform/aws

resource "aws_db_subnet_group" "db_subnet_group_sikdorak" {

  name = "db_subnet_sikdorak"

  subnet_ids = ["${data.terraform_remote_state.vpc.outputs.vpc_public_subnet}",
    "${data.terraform_remote_state.vpc.outputs.vpc_private_subnet}",
    "${data.terraform_remote_state.vpc.outputs.vpc_public_subnet2}",
    "${data.terraform_remote_state.vpc.outputs.vpc_private_subnet2}"
  ]

  tags = {
    Name = "be"
  }

}

resource "aws_vpc" "sikdorak_BE_VPC" {
  cidr_block       = "10.0.0.0/16"
  instance_tenancy = "default"

  tags = {
    Name = "VPC_BE_main"
  }
}

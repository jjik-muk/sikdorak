resource "aws_vpc" "sikdorak_BE_VPC" {
  cidr_block       = "10.0.0.0/16"
  instance_tenancy = "default"

  tags = {
    Name = "VPC_BE_main"
  }
}

resource "aws_subnet" "sikdorak_BE_public_subnet" {
  vpc_id     = aws_vpc.sikdorak_BE_VPC.id
  cidr_block = "10.0.10.0/24"

  availability_zone = "ap-northeast-2a"

  tags = {
    Name = "public-subnet-BE"
  }
}

resource "aws_subnet" "sikdorak_BE_private_subnet" {
  vpc_id     = aws_vpc.sikdorak_BE_VPC.id
  cidr_block = "10.0.11.0/24"

#  availability_zone = "ap-northeast-2b" # 일단 같은 가용영역에 할당

  tags = {
    Name = "public-subnet-BE"
  }
}

resource "aws_internet_gateway" "igw" {

    vpc_id = aws_vpc.sikdorak_BE_VPC.id

    tags = {
        Name = "igw-VPC-BE-main"
    }
}

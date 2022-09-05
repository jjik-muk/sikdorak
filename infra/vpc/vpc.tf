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
    Name = "private-subnet-BE"
  }
}

resource "aws_internet_gateway" "igw-be" {

  vpc_id = aws_vpc.sikdorak_BE_VPC.id

  tags = {
    Name = "igw-VPC-BE-main"
  }
}

resource "aws_route_table" "rt_BE_public_subnet" {
  vpc_id = aws_vpc.sikdorak_BE_VPC.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw-be.id
  }

  tags = {
    Name = "rt-BE-public-subnet"
  }
}

resource "aws_route_table_association" "rta_public" {
    subnet_id = aws_subnet.sikdorak_BE_public_subnet.id
    route_table_id = aws_route_table.rt_BE_public_subnet.id
  
}

# 임시 private 외부 연결

resource "aws_route_table" "rt_BE_private_subnet" {
  vpc_id = aws_vpc.sikdorak_BE_VPC.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw-be.id
  }

  tags = {
    Name = "rt-BE-private-subnet"
  }
}

resource "aws_route_table_association" "rta_private" {
    subnet_id = aws_subnet.sikdorak_BE_private_subnet.id
    route_table_id = aws_route_table.rt_BE_private_subnet.id
  
}
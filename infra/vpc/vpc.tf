resource "aws_vpc" "vpc_be" {
  cidr_block       = "10.0.0.0/16"
  instance_tenancy = "default"

  tags = {
    Name = "vpc-be"
  }
}

resource "aws_subnet" "public_subnet_be" {
  vpc_id     = aws_vpc.vpc_be.id
  cidr_block = "10.0.10.0/24"

  availability_zone = "ap-northeast-2a"

  tags = {
    Name = "public-subnet-be"
  }
}

resource "aws_subnet" "private_subnet_be" {
  vpc_id     = aws_vpc.vpc_be.id
  cidr_block = "10.0.11.0/24"

  #  availability_zone = "ap-northeast-2b" # 일단 같은 가용영역에 할당

  tags = {
    Name = "private-subnet-be"
  }
}

resource "aws_internet_gateway" "igw-be" {

  vpc_id = aws_vpc.vpc_be.id

  tags = {
    Name = "igw-be"
  }
}

resource "aws_route_table" "rt_public_subnet_be" {
  vpc_id = aws_vpc.vpc_be.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw-be.id
  }

  tags = {
    Name = "rt-public-subnet-be"
  }
}

resource "aws_route_table_association" "rta_public_subnet_be" {
    subnet_id = aws_subnet.public_subnet_be.id
    route_table_id = aws_route_table.rt_public_subnet_be.id
  
}

# 임시 private 외부 연결

resource "aws_route_table" "rt_private_subnet_be" {
  vpc_id = aws_vpc.vpc_be.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw-be.id
  }

  tags = {
    Name = "rt-private-subnet-be"
  }
}

resource "aws_route_table_association" "rta_private" {
    subnet_id = aws_subnet.private_subnet_be.id
    route_table_id = aws_route_table.rt_private_subnet_be.id
  
}
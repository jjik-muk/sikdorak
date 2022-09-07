resource "aws_subnet" "public_subnet_be2" {
  vpc_id     = aws_vpc.vpc_be.id
  cidr_block = "10.0.12.0/24"

  availability_zone = "ap-northeast-2b"

  tags = {
    Name = "public-subnet-be2"
  }
}

resource "aws_subnet" "private_subnet_be2" {
  vpc_id     = aws_vpc.vpc_be.id
  cidr_block = "10.0.13.0/24"

  availability_zone = "ap-northeast-2b"

  tags = {
    Name = "private-subnet-be2"
  }
}

resource "aws_route_table" "rt_public_subnet_be2" {
  vpc_id = aws_vpc.vpc_be.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw-be.id
  }

  tags = {
    Name = "rt-public-subnet-be"
  }
}

resource "aws_route_table_association" "rta_public_subnet_be2" {
  subnet_id      = aws_subnet.public_subnet_be2.id
  route_table_id = aws_route_table.rt_public_subnet_be2.id

}

# 임시 private 외부 연결

resource "aws_route_table" "rt_private_subnet_be2" {
  vpc_id = aws_vpc.vpc_be.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw-be.id
  }

  tags = {
    Name = "rt-private-subnet-be2"
  }
}

resource "aws_route_table_association" "rta_private2" {
  subnet_id      = aws_subnet.private_subnet_be2.id
  route_table_id = aws_route_table.rt_private_subnet_be2.id

}

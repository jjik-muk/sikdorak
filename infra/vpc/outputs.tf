output "vpc_id" {
  value       = aws_vpc.vpc_be.id
  description = "백엔드 vpc id"
}

output "vpc_public_subnet" {
  value = aws_subnet.public_subnet_be.id
  description = "백엔드 vpc public subnet"
}

output "vpc_private_subnet" {
  value = aws_subnet.private_subnet_be.id
  description = "백엔드 vpc private subnet"
}


output "vpc_public_subnet2" {
  value = aws_subnet.public_subnet_be2.id
  description = "백엔드 vpc public subnet2"
}

output "vpc_private_subnet2" {
  value = aws_subnet.private_subnet_be2.id
  description = "백엔드 vpc private subnet2"
}

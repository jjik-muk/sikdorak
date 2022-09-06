output "vpc_id" {
  value       = aws_vpc.vpc_be.id
  description = "백엔드 vpc id"
}

output "vpc_public_subnet" {
  value = aws_subnet.public_subnet_be.id
  description = "백엔드 vpc public subnet"
}

output "vpc_id" {
  value       = aws_vpc.vpc_be.id
  description = "백엔드 vpc id"
}

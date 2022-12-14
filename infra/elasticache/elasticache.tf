# elasticache에 subnet group 연결
resource "aws_elasticache_subnet_group" "elasticache-subnet-group" {
  name       = "elasticache-subnet-group"
  subnet_ids = [data.terraform_remote_state.vpc.outputs.vpc_private_subnet]
}

# create elasticache (redis)
resource "aws_elasticache_cluster" "single-node-redis-for-springboot" {
  cluster_id           = "single-node-redis-for-springboo"
  engine               = "redis"
  node_type            = "cache.t2.micro"
  num_cache_nodes      = 1 # node 1개
  parameter_group_name = "default.redis7" # 파라미터 그룹
  engine_version       = "7.0" # 레디스 버전
  port                 = 6379
  security_group_ids = [
    aws_security_group.security_group_redis.id
  ]

  subnet_group_name    = aws_elasticache_subnet_group.elasticache-subnet-group.name
}
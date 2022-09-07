# ecs 클러스터 생성
resource "aws_ecs_cluster" "ecs_cluster" {
  name = "my-cluster"
  tags = {
    Name = "ecs-be"
  }
}

## cloudwatch container log  -> 비용 발생
#resource "aws_cloudwatch_log_group" "log-group" {
#  name = "logs-ecs-cluster-springboot"

#  tags = {
#    Application = "logs-be"
#  }
#}

resource "aws_ecs_task_definition" "task_definition" {
  family = "worker"
  container_definitions = jsonencode([
    {
      name      = "worker"
      image     = "${data.terraform_remote_state.ecr.outputs.ecr_repository_url}:latest"
      cpu       = 1
      memory    = 512
      essential = true
    }
  ])
}

resource "aws_ecs_service" "worker" {
  name            = "worker"
  cluster         = aws_ecs_cluster.ecs_cluster.id
  task_definition = aws_ecs_task_definition.task_definition.arn
  desired_count   = 1
}


# reference : https://dev.to/thnery/create-an-aws-ecs-cluster-using-terraform-g80
# ECS,EC2에서 ECR 접근 권한 iam 생성
data "aws_iam_policy_document" "assume_role_policy" { # policy 읽기
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
  }
}

resource "aws_iam_role" "ecsTaskExecutionRole" { # iam role 생성
  name               = "ecs-execution-task-role"
  assume_role_policy = data.aws_iam_policy_document.assume_role_policy.json
  tags = {
    Name        = "be"
  }
}

resource "aws_iam_role_policy_attachment" "ecsTaskExecutionRole_policy" { # role attachment
  role       = aws_iam_role.ecsTaskExecutionRole.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonEC2ContainerServiceforEC2Role"
}

resource "aws_iam_instance_profile" "ecsTaskExecutionRole_profile" { # instance profile 생성
  name = "ecs-agent_profile"
  role = aws_iam_role.ecsTaskExecutionRole.name
}

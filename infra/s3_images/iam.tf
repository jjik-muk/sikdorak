resource "aws_iam_user" "s3_images_springboot" {

  name = "BE-springserver"

  tags = {

    Name = "BE-developers"

  }
}

resource "aws_iam_user_policy" "art_devops_black" {
  name = "s3_images_access"
  user = aws_iam_user.s3_images_springboot.name

  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": [
        "s3:*"
      ],
      "Effect": "Allow",
      "Resource": "${aws_s3_bucket.sikdorak_images.arn}"
    }
  ]

}
EOF
}

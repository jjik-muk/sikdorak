resource "aws_s3_bucket" "sikdorak_images" {
  bucket = var.s3_images_name

  tags = {
    Name = "sikdorak"
  }
}

resource "aws_s3_bucket_acl" "sikdora_images_acl" {
  bucket = aws_s3_bucket.sikdorak_images.id
  acl    = "public-read"
}

resource "aws_s3_bucket_versioning" "sikdorak_images_versioning" {
  bucket = aws_s3_bucket.sikdorak_images.id
  versioning_configuration {
    status = "Disabled"
  }
}

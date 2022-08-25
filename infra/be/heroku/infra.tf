# https://registry.terraform.io/providers/heroku/heroku/latest/docs/resources/app
resource "heroku_app" "BE" {
  name    = "${var.heroku_app_name}"
  region  = "us"

  buildpacks = [
    "heroku/gradle"
  ]
}

#resource "heroku_build" "BE" {
#  app_id      = heroku_app.BE.id
#  buildpacks  = ["https://github.com/heroku/heroku-buildpack-gradle.git"]
  
#  source {
#    url     = "https://github.com/jjik-muk/sikdorak"
#    version = "v0.0.1"
#  }
#}

output "Heroku_app_url" {
  value = "http://${heroku_app.BE.name}.herokuapp.com"
}
from django.db import models

# Create your models here.
from django.db import models

class Pokemon(models.Model):
    name = models.CharField(max_length=100)
    type = models.CharField(max_length=100)

    def __str__(self):
        return self.name

class Review(models.Model):
    pokemon = models.ForeignKey(Pokemon, related_name='reviews', on_delete=models.CASCADE)
    title = models.CharField(max_length=200)
    content = models.TextField()
    stars = models.IntegerField()

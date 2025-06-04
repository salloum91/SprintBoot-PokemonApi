from rest_framework import viewsets
from .models import Pokemon
from .serializers import PokemonSerializer
from rest_framework.response import Response
from rest_framework import status

class PokemonViewSet(viewsets.ModelViewSet):
    queryset = Pokemon.objects.all()
    serializer_class = PokemonSerializer

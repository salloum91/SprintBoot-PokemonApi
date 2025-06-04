from rest_framework import serializers
from .models import Pokemon, Review

class ReviewSerializer(serializers.ModelSerializer):
    class Meta:
        model = Review
        fields = ['id', 'title', 'content', 'stars']

class PokemonSerializer(serializers.ModelSerializer):
    reviews = ReviewSerializer(many=True, required=False)

    class Meta:
        model = Pokemon
        fields = ['id', 'name', 'type', 'reviews']

    def create(self, validated_data):
        reviews_data = validated_data.pop('reviews', [])
        pokemon = Pokemon.objects.create(**validated_data)
        for review_data in reviews_data:
            Review.objects.create(pokemon=pokemon, **review_data)
        return pokemon

    def update(self, instance, validated_data):
        reviews_data = validated_data.pop('reviews', [])
        instance.name = validated_data.get('name', instance.name)
        instance.type = validated_data.get('type', instance.type)
        instance.save()

        # Handle reviews update: clear old and add new
        instance.reviews.all().delete()
        for review_data in reviews_data:
            Review.objects.create(pokemon=instance, **review_data)

        return instance

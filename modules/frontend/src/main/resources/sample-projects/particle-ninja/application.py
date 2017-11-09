import random
from numbers import Number

particule = None
vitesseY = None
GRAVITE = None
particuleY = None
particuleX = None
deltaY = None
item = None
vitesseX = None
rotationParticule = None

"""Describe this function...
"""
def preparer_la_trajectoire():
  global particule, vitesseY, GRAVITE, particuleY, particuleX, deltaY, item, vitesseX, rotationParticule
  particuleX = random.randint(20, 300)
  particuleY = 420
  vitesseX = random.randint(-1, 8)
  vitesseY = random.randint(-18, -12)
  rotationParticule = 0

"""Describe this function...
"""
def deplacer_la_particule():
  global particule, vitesseY, GRAVITE, particuleY, particuleX, deltaY, item, vitesseX, rotationParticule
  deltaY = vitesseY
  vitesseY = (vitesseY if isinstance(vitesseY, Number) else 0) + GRAVITE
  particuleX = (particuleX if isinstance(particuleX, Number) else 0) + vitesseX
  particuleY = (particuleY if isinstance(particuleY, Number) else 0) + (vitesseY + deltaY) / 2
  particule.pos = particuleX,particuleY

def scheduled2():
  global particule, vitesseY, GRAVITE, particuleY, particuleX, deltaY, item, vitesseX, rotationParticule
  rotationParticule = (rotationParticule if isinstance(rotationParticule, Number) else 0) + 1
  particule.image=(str('particule') + str(rotationParticule % 4))


WIDTH=800
HEIGHT=420
particule = Actor('particule',anchor=('center','middle'),pos=(40,420))
GRAVITE = 0.35
preparer_la_trajectoire()

def draw():
  global particule, vitesseY, GRAVITE, particuleY, particuleX, deltaY, item, vitesseX, rotationParticule
  screen.blit('cosmos',(0,0))
  particule.draw()


def update():
  global particule, vitesseY, GRAVITE, particuleY, particuleX, deltaY, item, vitesseX, rotationParticule
  deplacer_la_particule()
  if particule.y > 500:
    preparer_la_trajectoire()


clock.schedule_interval(scheduled2,0.05)


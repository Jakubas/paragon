## Version 1.3.5
### Changed:
- Merged with newest Amber
  
## Version 1.3.4
### Changed:
- Fixed a bug in harvester that was causing some players to stop after 10-20 tiles by changing the way
  the next crop to harvest is calculated

## Version 1.3.3
### Changed:
- Improved speed and functionality of harvester bot, it is now officialy released
- Dragonfly catcher (and other automations) now work with horses
- Tuber digger can now also be used to dig clay

## Version 1.3.2
### Changed:
- Merged with latest vanilla changes

## Version 1.3.1
### Changed:
- Merged with latest vanilla and amber changes
- Removed sort button from cupboards until I get it working properly

## Version 1.3
### Added:
- LP/H/S (learning points/hour/size) and LP/H/S/W (learning points/hour/size/weight) info to curiosities
- Total LP/H to study information
- [WIP] Sort button for cupboards

### Changed:
- Merged with newest Amber
- Drinking water in farmer automation

## Version 1.2
### Changed:
- Autostudy now supports multiple cupboards
- merged with vanilla changes

## Version 1.1.2
### Changed:
- merged with upstream

## Version 1.1.1
### Changed:
- Added a dragonfly catcher automation

## Version 1.1
### Added:
- Added a tuber digger, that digs soil and drops it and keeps the Odd Tubers that it digs up. It moves east after it can't dig
  anymore in a particular area, so make sure that your path to the east is clear before running for extended periods.
- Added inventory utilities

### Fixed:
- The 'X' button on windows that are created by the automations will now close the window and stop the automation prematurely

### Changed:
- Inventory.drink will now drink from buckets as well
- Refactored paragon.Utils.java into its own package haven.paragon.utils and seperated it into multiple
  appropriate classes.
 


## Version 1.0
### Added:
- Farming automation that automates harvesting fields of any size
- Patrol bot that walks around a path and plays a sound if an enemy is found, 
the user must provide the sound file in the build directory in sounds/alarm.wav
- A path generator for the patrol bot	
- A bunch of hooks and utilities used by the Patrol and Farming automation

### Changed: 
- Added a button to the main game interface that appears after logging in. Clicking on this 
button provides the user with a list of bots that they can run
- Modified files in the haven package so that I could access the data and classes that I need 
for my automations

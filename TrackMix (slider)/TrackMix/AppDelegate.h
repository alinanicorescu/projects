//
//  AppDelegate.h
//  TrackMix
//
//  Created by Alina Nicorescu on 20/06/15.
//  Copyright (c) 2015 Alina Nicorescu. All rights reserved.
//

#import <Cocoa/Cocoa.h>
@class Track;

@interface AppDelegate : NSObject <NSApplicationDelegate>

@property (weak) IBOutlet NSTextField *textField;
@property (weak) IBOutlet NSSlider *slider;
@property (strong) Track *track;

- (IBAction)mute:(id)sender;

- (IBAction)takeFloateValueForVolumeFrom:(id)sender;

- (void)updateUserInterface;


@end


//
//  AppDelegate.m
//  TrackMix
//
//  Created by Alina Nicorescu on 20/06/15.
//  Copyright (c) 2015 Alina Nicorescu. All rights reserved.
//

#import "AppDelegate.h"
#import "Track.h"

@interface AppDelegate ()

@property (weak) IBOutlet NSWindow *window;
@end

@implementation AppDelegate

- (void)applicationDidFinishLaunching:(NSNotification *)aNotification {
    // Insert code here to initialize your application
    Track *aTrack = [[Track alloc] init];
    [self setTrack:aTrack];
    [self updateUserInterface];
}

- (void)applicationWillTerminate:(NSNotification *)aNotification {
    // Insert code here to tear down your application
}

- (IBAction)mute:(id)sender {
    NSLog(@"received a mute: message");
    [self.track setVolume:0.0];
    [self updateUserInterface];
}

- (IBAction)takeFloateValueForVolumeFrom:(id)sender {
    NSString *senderName = nil;
    if (sender == self.textField) {
        senderName = @ "textField";
    } else {
        senderName = @ "slider";
    }
    NSLog(@"%@ sent takeFloatValueForVolumeFrom: with value %1.2f", senderName, [sender floatValue]);
    float newValue = [sender floatValue];
    [self.track setVolume:newValue];
    [self updateUserInterface];
}

- (void) updateUserInterface {
    float volume = self.track.volume;
    [self.textField setFloatValue:volume];
    [self.slider setFloatValue:volume];
}
@end

//
//  SceneParameters.m
//  SkyBoxExample
//
//  Created by Alina Nicorescu on 03/10/15.
//  Copyright (c) 2015 Alina Nicorescu. All rights reserved.
//

#import "SceneParameters.h"

@implementation SceneParameters


-(SceneParameters*) init {
    if ([super init]) {
        SceneParameters* sceneParameters = [SceneParameters alloc];
        if (sceneParameters) {
            sceneParameters.fov = [NSNumber numberWithFloat:90.0f];
            sceneParameters.nearZ= [NSNumber numberWithFloat:0.1f];
            sceneParameters.farZ = [NSNumber numberWithFloat:1000.0f];
        
            sceneParameters.eyeX = [NSNumber numberWithFloat:0.0f];
            sceneParameters.eyeY= [NSNumber numberWithFloat:0.0f];
            sceneParameters.eyeZ = [NSNumber numberWithFloat:0.0f];
        
            sceneParameters.centerX = [NSNumber numberWithFloat:0.0f];
            sceneParameters.centerY= [NSNumber numberWithFloat:0.5f];
            sceneParameters.centerZ = [NSNumber numberWithFloat:0.5f];
        
            sceneParameters.upX = [NSNumber numberWithFloat:0.0f];
            sceneParameters.upY= [NSNumber numberWithFloat:0.5f];
            sceneParameters.upZ = [NSNumber numberWithFloat:0.0f];
        
            sceneParameters.rotation = [NSNumber numberWithFloat:0.0f];
            sceneParameters.rX = [NSNumber numberWithFloat:0.1f];
            sceneParameters.rY= [NSNumber numberWithFloat:0.1f];
            sceneParameters.rZ= [NSNumber numberWithFloat:0.1f];

            sceneParameters.size = [NSNumber numberWithFloat:0.4f];
            sceneParameters.cameraMoveVal = [NSNumber numberWithFloat:0.5f];
            return sceneParameters;
        }
    }
    return nil;
}

-(void) incrementCenterX {
    self.centerX = [NSNumber numberWithFloat: self.centerX.floatValue +
                    [self.cameraMoveVal floatValue]] ;
}
    
-(void) incrementCenterY {
    self.centerY = [NSNumber numberWithFloat: self.centerY.floatValue + [self.cameraMoveVal floatValue]];
}
      
    
-(void) decrementCenterX  {
    self.centerX = [NSNumber numberWithFloat: self.centerX.floatValue - [self.cameraMoveVal floatValue]];
}
        
-(void) decrementCenterY  {
    self.centerY = [NSNumber numberWithFloat: self.centerY.floatValue - [self.cameraMoveVal floatValue]];
}

@end

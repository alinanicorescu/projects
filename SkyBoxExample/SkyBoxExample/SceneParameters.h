//
//  SceneParameters.h
//  SkyBoxExample
//
//  Created by Alina Nicorescu on 03/10/15.
//  Copyright (c) 2015 Alina Nicorescu. All rights reserved.
//

#import <Foundation/Foundation.h>



@interface SceneParameters : NSObject

/*
 camera field of view
 */
@property(strong, nonatomic) NSNumber* fov;
@property(strong, nonatomic) NSNumber* nearZ;
@property(strong, nonatomic) NSNumber* farZ;

/*
eye coordinates
 */
@property(strong, nonatomic) NSNumber* eyeX;
@property(strong, nonatomic) NSNumber* eyeY;
@property(strong, nonatomic) NSNumber* eyeZ;

/*
 the target coordinates
 */
@property(strong, nonatomic) NSNumber* centerX;
@property(strong, nonatomic) NSNumber* centerY;
@property(strong, nonatomic) NSNumber* centerZ;

/*
 camera up coordinates
 */
@property(strong, nonatomic) NSNumber* upX;
@property(strong, nonatomic) NSNumber* upY;
@property(strong, nonatomic) NSNumber* upZ;

//rotation parameters
@property(strong, nonatomic) NSNumber* rotation;
@property(strong, nonatomic) NSNumber* rX;
@property(strong, nonatomic) NSNumber* rY;
@property(strong, nonatomic) NSNumber* rZ;

/*
 skybox size (x=y=z)
 */
@property(strong, nonatomic) NSNumber* size;

/*
 camera movement value for one mouse gesture
 */
@property(strong, nonatomic) NSNumber* cameraMoveVal;

-(void) incrementCenterX;
-(void) incrementCenterY;
-(void) decrementCenterX;
-(void) decrementCenterY;
   

@end

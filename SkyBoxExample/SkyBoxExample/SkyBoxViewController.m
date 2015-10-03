//
//  SkyBoxViewController.m
//  SkyBoxExample
//
//  Created by Alina Nicorescu on 03/10/15.
//  Copyright (c) 2015 Alina Nicorescu. All rights reserved.
//

#import "SkyBoxViewController.h"
#import "SceneParameters.h"

@interface SkyBoxViewController () {
    
}
@property (strong, nonatomic) EAGLContext *context;
@property (strong, nonatomic) GLKTextureInfo *cubemap;
@property (strong, nonatomic) GLKSkyboxEffect *skyboxEffect;
@property (strong, nonatomic) SceneParameters *sceneParameters;

- (void)setupGL;
- (void)tearDownGL;
@end


@implementation SkyBoxViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.context = [[EAGLContext alloc] initWithAPI:kEAGLRenderingAPIOpenGLES3];
    
    if (!self.context) {
        NSLog(@"Failed to create ES context");
    }
    self.sceneParameters = [[SceneParameters alloc] init];
    [self setupGLKView];
    [self setupGL];
}


-(void) setupGLKView {
    GLKView *view = (GLKView *)self.view;
    view.context = self.context;
    view.drawableDepthFormat = GLKViewDrawableDepthFormat24;
    view.multipleTouchEnabled = YES;
    view.userInteractionEnabled = YES;
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    [self tearDownGL];
    
    if ([EAGLContext currentContext] == self.context) {
        [EAGLContext setCurrentContext:nil];
    }
    self.context = nil;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc. that aren't in use.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
    } else {
        return YES;
    }
}

- (void)setupGL
{
    [EAGLContext setCurrentContext:self.context];
    
    self.skyboxEffect = [[GLKSkyboxEffect alloc] init];
    
    //enable depth buffers
    glEnable(GL_DEPTH_TEST);
    
    
    NSArray* cubeMapFileNamesArray = [self p_makeCubeMapFileNamesArray:@{@"right": @"right", @"left": @"left", @"up":@"up", @"down":@"down", @"front":@"front", @"back" :@"back"} inPath:@"Miracle" withImageType:@"bmp"];
    
    NSError *error;
    NSDictionary *options = [NSDictionary dictionaryWithObject:[NSNumber numberWithBool:YES]
                                                        forKey:GLKTextureLoaderOriginBottomLeft];
    self.cubemap = [GLKTextureLoader cubeMapWithContentsOfFiles:cubeMapFileNamesArray
                                                        options:options
                                                          error:&error];
    if (error) {
        NSLog(@"The cubemap effect could not be created!");
    }
    self.skyboxEffect.textureCubeMap.name = self.cubemap.name;
    
    //glBindVertexArrayOES(0);
}

- (void)tearDownGL
{
    [EAGLContext setCurrentContext:self.context];
    
    //glDeleteBuffers(1, &vertexBuffer);
    //glDeleteVertexArraysOES(1, &vertexArray);
    self.skyboxEffect = nil;
    
}

-(NSArray*)p_makeCubeMapFileNamesArray : (NSDictionary*) fileNames inPath: (NSString*) path withImageType: (NSString*) imageType {
    
    if (!fileNames || [fileNames count] < 6) {
        return nil;
    }
    
    NSString * usingPath;
    if (!path) {
        usingPath = path;
    } else {
        usingPath = @"";
    }
    NSString* usingImageType;
    if  (!imageType) {
        usingImageType = imageType;
    } else {
        usingImageType = @"png";
    }
    
    NSArray *cubeMapFileNames = @[
                                  [[NSBundle mainBundle] pathForResource:
                                   [[path stringByAppendingString:@"/"] stringByAppendingString:[fileNames valueForKey:@"right"]] ofType: imageType],
                                  [[NSBundle mainBundle] pathForResource:
                                   [[path stringByAppendingString:@"/"] stringByAppendingString:[fileNames valueForKey:@"left"]] ofType: imageType],
                                  [[NSBundle mainBundle] pathForResource:
                                   [[path stringByAppendingString:@"/"] stringByAppendingString:[fileNames valueForKey:@"up"]] ofType: imageType],
                                  [[NSBundle mainBundle] pathForResource:
                                   [[path stringByAppendingString:@"/"] stringByAppendingString:[fileNames valueForKey:@"down"]] ofType: imageType],
                                  [[NSBundle mainBundle] pathForResource:
                                   [[path stringByAppendingString:@"/"] stringByAppendingString:[fileNames valueForKey:@"front"]] ofType: imageType],
                                  [[NSBundle mainBundle] pathForResource:
                                   [[path stringByAppendingString:@"/"] stringByAppendingString:[fileNames valueForKey:@"back"]] ofType: imageType]];
    
    
    return cubeMapFileNames;
}

#pragma mark - GLKView and GLKViewController delegate methods

- (void)update
{
    //prepare projection matrix
    //aspect ratio of view frustrum is equal to screen aspect ratio
    float aspect = fabsf(self.view.bounds.size.width / self.view.bounds.size.height);
    float fov = [self.sceneParameters.fov floatValue];
    float nearZ= [self.sceneParameters.nearZ floatValue];
    float farZ = [self.sceneParameters.farZ floatValue];
    
    //set projection matrix
    GLKMatrix4 projectionMatrix = GLKMatrix4MakePerspective(GLKMathDegreesToRadians(fov), aspect, nearZ, farZ);
    self.skyboxEffect.transform.projectionMatrix = projectionMatrix;
    
    
    //eye is in the center of the cube
    float eyeX = [self.sceneParameters.eyeX floatValue];
    float eyeY = [self.sceneParameters.eyeY floatValue];
    float eyeZ = [self.sceneParameters.eyeZ floatValue];
    
    //the point to look at, can change as a result of user gesture
    float centerX = [self.sceneParameters.centerX floatValue];
    float centerY = [self.sceneParameters.centerY floatValue];
    float centerZ = [self.sceneParameters.centerZ floatValue];
    
    //up vector of the camera
    float upX = [self.sceneParameters.upX floatValue];
    float upY = [self.sceneParameters.upY floatValue];
    float upZ = [self.sceneParameters.upZ floatValue];
    
    
    //prepare model view matrix
    GLKMatrix4 modelViewMatrix   =
    GLKMatrix4MakeLookAt(eyeX, eyeY, eyeZ,
                         centerX, centerY, centerZ, upX, upY, upZ);
    
    //model rotation
    float rX = [self.sceneParameters.rX floatValue];
    float rY = [self.sceneParameters.rY floatValue];
    float rZ = [self.sceneParameters.rZ floatValue];
    float rotation = [self.sceneParameters.rotation floatValue];
    modelViewMatrix = GLKMatrix4Rotate(modelViewMatrix, rotation, rX, rY, rZ);
    
    //set model view matrix
    self.skyboxEffect.transform.modelviewMatrix = modelViewMatrix;
    
    float size = [self.sceneParameters.size floatValue];
    self.skyboxEffect.xSize=size;
    self.skyboxEffect.ySize=size;
    self.skyboxEffect.zSize=size;
}

- (void)glkView:(GLKView *)view drawInRect:(CGRect)rect
{
    //set background color
    glClearColor(0.65f, 0.65f, 0.65f, 1.0f);
    
    //clear buffers
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
    
    //draw the skybox
    [self.skyboxEffect prepareToDraw];
    [self.skyboxEffect draw];
    
}

- (IBAction)handleRotation:(UIRotationGestureRecognizer*)sender {
    self.sceneParameters.rotation = [NSNumber numberWithFloat:sender.rotation];
}



-(IBAction)handleSwipeLeft:(UISwipeGestureRecognizer*) sender {
    [self p_lookLeft];
}

-(IBAction)handleSwipeRight:(UISwipeGestureRecognizer*) sender {
    [self p_lookRight];
}

-(IBAction)handleSwipeUp:(UISwipeGestureRecognizer*) sender {
    [self p_lookUp];
}

-(IBAction)handleSwipeDown:(UISwipeGestureRecognizer*) sender {
    [self p_lookDown];
}

- (void) p_lookDown {
    [self.sceneParameters decrementCenterY];
    
}

-(void) p_lookUp {
    [self.sceneParameters incrementCenterY];
}

-(void) p_lookLeft {
    [self.sceneParameters decrementCenterX];
}

-(void) p_lookRight {
    [self.sceneParameters incrementCenterX];
}

@end

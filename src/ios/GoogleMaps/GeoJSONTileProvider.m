//
//  GeoJSONTileProvider.m
//  app
//
//  Created by Ramiro Gonzalez Maciel on 6/8/15.
//
//

#import "GeoJSONTileProvider.h"

@implementation GeoJSONTileProvider
@synthesize webView, commandDelegate;

/**
 * requestTileForX:y:zoom:receiver: generates image tiles for GMSTileOverlay.
 * It must be overridden by subclasses. The tile for the given |x|, |y| and
 * |zoom| _must_ be later passed to |receiver|.
 *
 * Specify kGMSTileLayerNoTile if no tile is available for this location; or
 * nil if a transient error occured and a tile may be available later.
 *
 * Calls to this method will be made on the main thread. See GMSSyncTileLayer
 * for a base class that implements a blocking tile layer that does not run on
 * your application's main thread.
 */
- (void)requestTileForX:(NSUInteger)x
                      y:(NSUInteger)y
                   zoom:(NSUInteger)zoom
               receiver:(id<GMSTileReceiver>)receiver {
    if ( zoom >= 12 ) {
//        NSLog(@"tileRequested %lu %lu %lu", (unsigned long)x, (unsigned long)y, (unsigned long)zoom);

/* Al webView lo tengo que llamar en el main thread. Aunque parece lógico ejecutarlo en background con algo como:
        [self.commandDelegate runInBackground:^{
 Si lo hago así la app se rompe con:
 bool _WebTryThreadLock(bool), 0x1702120c0: Tried to obtain the web lock from a thread other than the main thread or the web thread. This may be a result of calling to UIKit from a secondary thread. Crashing now...
 1   0x191f46a74 WebThreadLock
 */
        dispatch_sync(dispatch_get_main_queue(), ^{
            NSString* call = [NSString stringWithFormat:@"window.MapTileRequestedHandler(%lu,%lu,%lu);", (unsigned long)zoom,(unsigned long)x,(unsigned long)y];
//            NSLog(@"calling %@", call);
            NSString *returnvalue = [self.webView stringByEvaluatingJavaScriptFromString: call];
//            NSLog(@"returnvalue %@", returnvalue);
     });
//        }];
        
    };
    [receiver receiveTileWithX: x y: y zoom: zoom image: nil];

}

+ (instancetype)tileLayer {
    id instance = [[ GeoJSONTileProvider alloc] init];
    return instance;
}

@end

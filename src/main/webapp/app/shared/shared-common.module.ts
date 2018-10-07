import { NgModule } from '@angular/core';

import { JhHotelSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [JhHotelSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [JhHotelSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class JhHotelSharedCommonModule {}

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IntnetServicesComponent } from './intnet-services.component';

describe('IntnetServicesComponent', () => {
  let component: IntnetServicesComponent;
  let fixture: ComponentFixture<IntnetServicesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IntnetServicesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IntnetServicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

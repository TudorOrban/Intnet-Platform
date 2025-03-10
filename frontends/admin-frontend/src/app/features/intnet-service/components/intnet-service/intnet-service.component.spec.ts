import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IntnetServiceComponent } from './intnet-service.component';

describe('IntnetServiceComponent', () => {
  let component: IntnetServiceComponent;
  let fixture: ComponentFixture<IntnetServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IntnetServiceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IntnetServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { PitchAppPage } from './app.po';

describe('pitch-app App', function() {
  let page: PitchAppPage;

  beforeEach(() => {
    page = new PitchAppPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});

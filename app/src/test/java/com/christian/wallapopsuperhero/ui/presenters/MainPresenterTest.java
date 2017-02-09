package com.christian.wallapopsuperhero.ui.presenters;

import android.os.AsyncTask;

import com.christian.wallapopsuperhero.data.Comic;
import com.christian.wallapopsuperhero.net.CapitainAmericaComicsParser;
import com.christian.wallapopsuperhero.net.CapitainAmericaComicsResults;
import com.christian.wallapopsuperhero.ui.MainView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class MainPresenterTest {

    private ArrayList<Comic> comics;

    @Mock
    private CapitainAmericaComicsParser mockCapitainAmericaComicsParser;

    @Mock
    private MainView mockMainView;

    protected ArrayList<Comic> getComics() {
        ArrayList<Comic> comics = new ArrayList<>();

        for (int i = 0; i < 2; ++i) {
            Comic c = new Comic();
            c.setId(i);
            c.setTitle("Title " + i);
            c.setDescription("Description " + i);
            c.setVariantDescription("Variant description " + i);
            c.setUrl("fake://url/"+i);
            comics.add(c);
        }

        return comics;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadComics() {
        comics = getComics();

        final MainPresenter presenter = new MainPresenter();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                presenter.loadResults(comics, 2);

                return null;
            }
        }).when(mockCapitainAmericaComicsParser).execute();
        when(mockCapitainAmericaComicsParser.setListener(any(CapitainAmericaComicsResults.class)))
                .thenReturn(mockCapitainAmericaComicsParser);
        when(mockCapitainAmericaComicsParser.setOffset(any(Integer.class)))
                .thenReturn(mockCapitainAmericaComicsParser);

        presenter.setView(mockMainView);
        presenter.setClient(mockCapitainAmericaComicsParser);
        presenter.resume();

        assertThat(presenter.getComics(), is(equalTo(comics)));
    }


}

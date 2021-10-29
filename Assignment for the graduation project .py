import requests
from pprint import pprint
import urllib.request
import json
from tqdm import tqdm, trange
from time import sleep




token_ya = input('Введите токен от Яндекс Диска: ')
token_vk = input('Введите токен от Вконакте: ')


def add_folder_ya_disk():
    url = 'https://cloud-api.yandex.net/v1/disk/resources/'
    headers = {'Content-Type': 'application/json',
               'Authorization': f'OAuth {token_ya}'}
    params = {'path': 'netology'}
    response = requests.put(url , headers=headers, params=params)
    r = response.json()
    print('Создал папку в Яндекс Диске')
    sleep(0.5)
    return r

def photo_avatar_vk():
    url = f'https://api.vk.com/method/photos.get'
    params = {'album_id':'profile',
              'extended':'1',
              'photo_sizes':'1',
              'access_token': f'{token_vk}',
              'v':'5.130'}
    responce = requests.get(url, params=params  )
    r = responce.json()
    return r

def download_in_Yandex():
    list_photo = []
    dict_result = {}
    list_result = []
    photo_avatar_vk()

    for i in trange(100, desc='Загружаю фото с профиля Вконтакте', bar_format='{desc} : {percentage} %' ):
        sleep(0.01)
    print('Готово')
    sleep(1)

    for photo in tqdm((photo_avatar_vk()['response']['items']), desc='Сортирую фотографии максимального размера!', bar_format='{desc} : {percentage} %'):
        sleep(0.5)
        height = 0
        width = 0
        url_ = ''

        for size in photo['sizes']:
            if size['height'] > height:
                height = size['height']
                url_ = size['url']
                width = size['width']

        likes = photo['likes']['count']

        if likes in dict_result.keys():
            likes += 0.1
            list_photo.append({likes : url_})

        else:
            list_photo.append({likes:url_})

        dict_result[likes] =f'{height}*{width}'

    print('Готово')
    add_folder_ya_disk()

    n = int(input(f'Введите количество фото, которые хотите сохранить на Яндекс Диск? У вас их {len(list_photo)}: '))

    if n <= len(list_photo):
        for i in trange(n, desc='Закачиваю фото на Яндекс Диск', bar_format='{desc} : {percentage} %', mininterval=0.05):
            for name_photo, url_photo in list_photo[i].items():

                urllib.request.urlretrieve(url_photo, f'{name_photo}.jpeg')
                disk_file_path = f'/netology/{name_photo}.jpeg'  # директория на диске
                file_name = f'{name_photo}.jpeg'    # файл в текущей директории

                def get_upload_link():
                    upload_url = 'https://cloud-api.yandex.net/v1/disk/resources/upload'
                    headers = {'Content-Type': 'application/json',
                               'Authorization': f'OAuth {token_ya}'}
                    params = {'path': disk_file_path, 'overwrite': 'true', 'fields': 'href'}
                    response = requests.get(upload_url, headers=headers, params=params)
                    return response.json()

                def upload_to_disk(file_name):

                    href = get_upload_link().get('href')
                    response = requests.put(href, data=open(file_name, 'rb'))
                    response.raise_for_status()

                    if response.status_code != 201:
                        print()
                        print(f'Произошла ошибка в закачивании фото {name_photo}')

                get_upload_link()

                upload_to_disk(file_name)

                temporary_list = []
                temporary_list.append({'file_name':f'{name_photo}.jpeg', 'size': f'{dict_result[name_photo]}'})
                list_result.append(temporary_list)

    else:
        print(f'У вас нет столько фотографий в профиле, у вас в профиле только  {len(list_photo)} фотографии, введите еще раз!')
        download_in_Yandex()

    print('Поздравляю, ваши фотографии успешно скачаны с сайта Вконтакте и помещены в Яндекс Диск!')

    with open('result.json', 'w') as f:
        json.dump(list_result, f , ensure_ascii=False, indent=2)

    print('json файл с результатом успешно создан!')

download_in_Yandex()


